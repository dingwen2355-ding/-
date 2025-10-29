
export function fSetData(obj, data) {
    obj = JSON.parse(JSON.stringify(obj))
    data.forEach(key => {
        delete obj[key];
    });
    return obj;
};