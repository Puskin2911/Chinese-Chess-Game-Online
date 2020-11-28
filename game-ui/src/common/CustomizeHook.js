import React from "react";


export default function useDidUpdateEffect(callback, deps = []) {
    const isFirstRender = React.useRef(true);

    React.useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false;

        } else {
            callback();
        }
    }, [callback, deps]);
}