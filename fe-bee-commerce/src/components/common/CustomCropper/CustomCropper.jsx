import {useRef, useState} from "react";
import {ReactCrop} from "react-image-crop";
import Button from "../Button";
import Wrapper from "../Wrapper";

function CustomCropper({ src, aspect, onDone = () => {}, onCancel = () => {} }) {
    const [cropper, setCropper] = useState({
        unit: "%",
        width: 100,
        height: 100,
        aspect: aspect,
    });
    const canvasRef = useRef(null);
    const baseImage = useRef(null);
    const displayImage = useRef(null);

    console.log("displayImage: ", displayImage);

    const handleCropComplete = (crop) => {
        if (crop.width && crop.height && displayImage.current) {
            const canvas = canvasRef.current;
            const image = displayImage.current;
            const scaleX = image.width / image.naturalWidth;
            const scaleY = image.height / image.naturalHeight;

            const ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            canvas.width = crop.width / scaleX;
            canvas.height = crop.height / scaleY;

            console.log({
                scaleX,
                scaleY,
                crop,
            });

            ctx.drawImage(
                image,
                crop.x / scaleX,
                crop.y / scaleY,
                crop.width / scaleX,
                crop.height / scaleY,
                0,
                0,
                crop.width / scaleX,
                crop.height / scaleY,
            );
        }
    };

    const handleDone = () => {
        const canvas = canvasRef.current;
        const dataURL = canvas.toDataURL();
        onDone(dataURL);
    };

    return (
        <>
            <div className="fixed flex justify-center align-items-center top-0  left-0 z-[999] h-screen w-screen bg-blue-400 bg-opacity-30">
                <Wrapper className="py-4 px-5 w-100 max-w-[500px] max-h-[90vh] overflow-y-scroll">
                    <div className="flex flex-col justify-between align-items-center mb-4">
                        <ReactCrop
                            aspect={aspect}
                            className="w-100"
                            crop={cropper}
                            onChange={(c) => setCropper(c)}
                            onComplete={handleCropComplete}
                        >
                            <img ref={displayImage} className="w-100" src={src} alt="" />
                        </ReactCrop>
                        <div className="flex">
                            <Button className="mx-3 rounded-md " onClick={handleDone}>
                                Lưu
                            </Button>
                            <Button className="mx-3 rounded-md " onClick={onCancel}>
                                Hủy
                            </Button>
                        </div>
                    </div>
                </Wrapper>
                {<canvas className="d-none" ref={canvasRef} />}
            </div>
        </>
    );
}

export default CustomCropper;
