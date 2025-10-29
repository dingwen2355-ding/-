const DataURLToFile = (dataUrl, filename = "file", type) => {
    let arr = dataUrl.split(",");
    let bstr = atob(arr[1]);
    !type && (type = arr[0].replace("data:", "").replace(";base64", ""));

    let n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type });
}

export const getCurCameraImage = (isLoadFile, filename = "file") => {
    let video = document
        .getElementById(window.RTCPlayer.divId)
        .querySelector("video");

    let canvas = document.createElement("canvas");
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;
    canvas
        .getContext("2d")
        .drawImage(video, 0, 0, canvas.width, canvas.height);
    let base64 = canvas.toDataURL("image/png", 1);

    if (isLoadFile) loadFile(base64, filename);
    else return base64;
}

export const loadFile = (content, filename = "file") => {
    const _content = DataURLToFile(content, filename)
    const aLink = document.createElement("a");
    aLink.download = filename;
    aLink.href = URL.createObjectURL(_content);
    aLink.click();
    URL.revokeObjectURL(_content);
}
