import React from "react";

export default function LoadingIndicator() {
    return (
        <div className="d-flex align-items-center justify-content-center position-fixed bg-dark w-100 h-100">
            <div className="spinner-border text-primary text-center mt-5" role="status">
                <span className="sr-only">Loading...</span>
            </div>
        </div>
    );
}