package com.beeecommerce.service.impl;

import com.beeecommerce.entity.*;
import com.beeecommerce.repository.AddressRepository;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.repository.WardRepository;
import com.beeecommerce.service.GHNService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.beeecommerce.constance.ApiPaths.GHNPath.GHN_PREFIX;

@Service
@RequiredArgsConstructor
public class GHNServiceImpl implements GHNService {
    private final ProductRepository productRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;

    @Override
    public Long calculateShippingFee(Long addressId, Long vendorsId, List<Long> productIdList) {
        long totalWeight = 0;
        long size = 0;
        long height = 0;
        long length = 0;
        long width = 0;

        // Tính toán thông tin sản phẩm từ danh sách productId
        Optional<Product> productOptional = null;
        for (Long productId : productIdList) {
            productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                totalWeight += product.getWeight();

                // Tính kích thước trung bình của sản phẩm
                height += product.getHeight();
                length += product.getLength();
                width += product.getWidth();
            }
        }

        // Tính toán kích thước trung bình của danh sách sản phẩm
        int productCount = productIdList.size();
        if (productCount > 0) {
            height /= productCount;
            length /= productCount;
            width /= productCount;
        }

        // Tính toán kích thước (size) của gói hàng
        size = (long) Math.cbrt(height * length * width);

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
        int serviceId = 53320;


        Address adr = addressRepository.findById(addressId).orElse(null);
        Ward ward = wardRepository.findById(adr.getWard().getId()).orElse(null);

        // người gửi
        Long from_district_id = productOptional.get().getVendors().getAddress().getWard().getDistrict().getId();
        String from_ward_code = productOptional.get().getVendors().getAddress().getWard().getCode();
        System.out.println(productOptional.get().getVendors().getAddress().getWard().getDistrict().getId() + "--");
        // người nhận
        String toWardCode = ward != null ? ward.getCode() : "";
        Long toDistrictId = ward != null ? ward.getId() : 0L;

        // Tạo JSON data để gửi đến API
        String jsonData = String.format("{\"shop_id\": %d, \"height\": %d, \"length\": %d, \"width\": %d, \"size\": %d, \"weight\": %d, \"to_district_id\": %d, \"service_id\": %d, \"to_ward_code\": \"%s\", \"from_ward_code\": \"%s\", \"from_district_id\": %d }",
                vendorsId, height, length, width, size, totalWeight, toDistrictId, serviceId, toWardCode,
                from_ward_code, from_district_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Token", "d2271479-e6a8-11ed-bc91-ba0234fcde32");

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Xử lý phản hồi từ API và trích xuất thông tin vận chuyển
            String responseBody = response.getBody();
            Map<String, Object> shippingData = extractShippingInfoFromResponse(responseBody);
            if (shippingData.containsKey("totalPrice")) {
                // Trả về giá vận chuyển được tính toán từ phản hồi API
                return (Long) shippingData.get("totalPrice");
            }
        }

        // Trả về giá trị mặc định nếu không có thông tin giá vận chuyển
        return 0L;
    }

    // Phương thức để trích xuất thông tin vận chuyển từ phản hồi JSON
    private Map<String, Object> extractShippingInfoFromResponse(String responseBody) {
        Map<String, Object> shippingData = new HashMap<>();
        try {
            // Phân tích chuỗi JSON để trích xuất thông tin vận chuyển
            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            if (jsonNode.has("data")) {
                JsonNode dataNode = jsonNode.get("data");
                if (dataNode.has("total")) {
                    shippingData.put("totalPrice", dataNode.get("total").asLong());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shippingData;
    }
}
