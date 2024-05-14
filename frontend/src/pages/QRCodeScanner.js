import { useEffect, useRef, useState } from "react";
import axios from 'axios';

import "../styles/QRCodeScanner.css";

import QrScanner from "qr-scanner";


const QrReader = () => {

    const scanner = useRef(null);
    const videoEl = useRef(null);
    const qrBoxEl = useRef(null);
    const [qrOn, setQrOn] = useState(true);
    const [verified, setVerified] = useState(null);
    const [errorText, setErrorText] = useState("");
    const signedJwt = process.env.REACT_APP_SIGNED_JWT;
    const issuer = process.env.REACT_APP_ISSUER;

    const [scannedResult, setScannedResult] = useState("");

    const onScanSuccess = async (result) => {
        if(!scannedResult){
            setScannedResult(result?.data);
        }
        // const signedJwt = await axios.get(result?.data);
        const response = await axios.post("http://localhost:8081/api/verify-vc", {
            "issuer": issuer,
            "digitalSignature": signedJwt
        });
        setScannedResult(signedJwt)
        console.log(response.data)

        setVerified(!response.data.verified)
        if (!response.data.verified) {
            setErrorText(response.data.message)
        }
    };

    const onScanFail = (err) => {
    };

    useEffect(() => {
        if (videoEl?.current && !scanner.current) {
            scanner.current = new QrScanner(videoEl?.current, onScanSuccess, {
                onDecodeError: onScanFail,
                preferredCamera: "environment",
                highlightScanRegion: true,
                highlightCodeOutline: true,
                overlay: qrBoxEl?.current || undefined,
            });

            scanner?.current
                ?.start()
                .then(() => setQrOn(true))
                .catch((err) => {
                    if (err) setQrOn(false);
                });
        }

        return () => {
            if (!videoEl?.current) {
                scanner?.current?.stop();
            }
        };
    }, []);

    useEffect(() => {
        if (!qrOn)
            alert(
                "Camera is blocked or not accessible. Please allow camera in your browser permissions and Reload."
            );
    }, [qrOn]);

    const closeSuccessOverlay = () => {
        setVerified(null);
    }

    return (
        <div className="qr-reader">
            <video ref={videoEl}></video>
            <div ref={qrBoxEl} className="qr-box">
            </div>

            {verified && (
                <div className="success-overlay">
                    <div className="success-content relative">
                        <button className="absolute top-2 right-4" onClick={closeSuccessOverlay}>✕</button>
                        <div className="tick">✔</div>
                        <p className="font-bold text-lg">Verified</p>
                    </div>
                </div>
            )}

            {verified===false && (
                <div className="success-overlay">
                    <div className="success-content relative">
                        <button className="absolute top-2 right-4" onClick={closeSuccessOverlay}>✕</button>
                        <div className="cross">✕</div>
                        <p className="font-bold mb-2 text-lg">Not Verified</p>
                        <p>{errorText}</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default QrReader;