import Input from "~/components/common/Input";
import Wrapper from "~/components/common/Wrapper";
import {CreateProductContext} from "~/pages/CreateProduct/CreateProduct";
import {useContext, useEffect} from "react";
import {useForm} from "react-hook-form";

function TransportInformation() {
    const { productData, setProductData, setValidator } = useContext(CreateProductContext);

    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm({
        mode: "onBlur",
        reValidateMode: "onChange",
    });

    useEffect(() => {
        const result = handleSubmit((data) => {
            console.log("Form transport is validating: ", data);
        });

        setValidator((prev) => [...prev, result]);
    }, []);

    const width = watch("width");
    const height = watch("height");
    const length = watch("length");
    const weight = watch("weight");

    useEffect(() => {
        setProductData({
            ...productData,
            width,
            height,
            length,
            weight,
        });
    }, [width, height, length, weight]);

    return (
        <Wrapper className="p-4 mb-9">
            <h1 className="text-2xl font-semibold mb-3">Thông tin Vận chuyển</h1>
            <div className="flex mb-4">
                <div className="w-[250px] text-right pr-8">Cân nặng (kg)</div>
                <div className="flex-1">
                    <Input
                        inputRef={{
                            ...register("weight", {
                                min: { message: "Khối lượng phải lớn hơn 0", value: 0 },
                                required: { message: "Khối lượng không được để trống", value: true },
                            }),
                        }}
                        errorMessage={errors.weight?.message}
                    />
                </div>
            </div>
            <div className="flex mb-4">
                <div className="w-[250px] text-right pr-8">Kích thước (cm)</div>
                <div className="flex-1">
                    <div className="grid grid-cols-3 gap-x-5">
                        <Input
                            inputRef={{
                                ...register("length", {
                                    min: { message: "Chiều dài phải lớn hơn 0", value: 0 },
                                    required: { message: "Chiều dài không được để trống", value: true },
                                }),
                            }}
                            errorMessage={errors.length?.message}
                            placeholder="Chiều dài"
                        />
                        <Input
                            inputRef={{
                                ...register("width", {
                                    min: { message: "Chiều rộng phải lớn hơn 0", value: 0 },
                                    required: { message: "Chiều rộng không được để trống", value: true },
                                }),
                            }}
                            errorMessage={errors.width?.message}
                            placeholder="Chiều rộng"
                        />
                        <Input
                            inputRef={{
                                ...register("height", {
                                    min: { message: "Chiều cao phải lớn hơn 0", value: 0 },
                                    required: { message: "Chiều cao không được để trống", value: true },
                                }),
                            }}
                            errorMessage={errors.height?.message}
                            placeholder="Chiều cao"
                        />
                    </div>
                </div>
            </div>
        </Wrapper>
    );
}

export default TransportInformation;
