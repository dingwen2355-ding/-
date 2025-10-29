export class DataToData {
    constructor() {
    }

    ToData(data) {
        var newData = this.DataToDataOne(data);
        var newData = this.DataToDataTwo(newData);
        var newData = this.DataToDataThree(newData);
        var newData = this.DataToDataFour(newData);
        return newData;
    }

    //格式化后台返回的数据
    DataToDataOne(Data) {
        const myMap = new Map();
        for (let i = 0; i < Data.length; i++) {
            let item = Data[i];
            let oneData = {};
            let num = Data[i].num;
            if (myMap.has(item.time)) {
                oneData = myMap.get(item.time);
            } else {
                oneData = {
                    time: Data[i].time,
                };
            }
            if (oneData.zeroStatusNum == null || oneData.zeroStatusNum == undefined) {
                oneData.zeroStatusNum = 0;
            }
            if (oneData.oneStatusNum == null || oneData.oneStatusNum == undefined) {
                oneData.oneStatusNum = 0;
            }
            if (item.status == 0) {
                oneData.zeroStatusNum = num + oneData.zeroStatusNum;
            }
            if (item.status == 1) {
                oneData.oneStatusNum = num + oneData.oneStatusNum;
            }
            myMap.set(item.time, oneData);
        }

        const valuesIterator = myMap.values();
        const valuesArray = Array.from(valuesIterator);
        return valuesArray;
    }

    //数据转为添加时间戳与日期格式
    DataToDataTwo(Data) {
        var newData = [];
        for (let i = 0; i < Data.length; i++) {
            let item = Data[i];
            item.timestamp = Date.parse(item.time);
            item.timeShow = this.MonthAndDay(item.time);
            newData.push(item);
        }
        return newData;
    }

    //日期格式转为其他样式显示
    MonthAndDay(dateString) {
        let date = new Date(dateString);
        let month = date.getMonth() + 1;
        let day = date.getDate();
        let formattedDate = `${month}.${day < 10 ? "0" : ""}${day}`;
        return formattedDate;
    }

    //数据转为添加时间戳与日期格式
    DataToDataThree(Data) {
        Data.sort((item1, item2) => item2.timestamp - item1.timestamp);
        let firstTwoItems = Data.slice(0, 7);
        firstTwoItems.sort((item1, item2) => item1.timestamp - item2.timestamp);
        return firstTwoItems;
    }

    //取出最大 + 最小值 + 平均值 + x 轴 + y轴
    DataToDataFour(Data) {
        let maxCombinedValue = -Infinity;
        let minCombinedValue = Infinity;

        let zeroArray = [];
        let oneArray = [];
        let dateArray = [];

        for (let i = 0; i < Data.length; i++) {
            const item = Data[i];
            zeroArray.push(Data[i].zeroStatusNum);
            oneArray.push(Data[i].oneStatusNum);
            dateArray.push(Data[i].timeShow);

            const combinedValue = item.zeroStatusNum + item.oneStatusNum;
            if (combinedValue > maxCombinedValue) {
                maxCombinedValue = combinedValue;
            }
            if (combinedValue < minCombinedValue) {
                minCombinedValue = combinedValue;
            }

        }

        let maxValue = 0;
        let minValue = 0;
        let result = 0;
        if (Data.length > 0) {
            maxValue = Math.max(maxCombinedValue);
            minValue = Math.min(minCombinedValue);
            result = Math.floor((maxValue + minValue) / 5);
            maxValue = maxCombinedValue;
            minValue = minCombinedValue;
        }


        return {
            data: Data,
            min: maxValue,
            max: minValue,
            interval: result,
            zeroArray: zeroArray,
            oneArray: oneArray,
            dateArray: dateArray,
        };
    }
}