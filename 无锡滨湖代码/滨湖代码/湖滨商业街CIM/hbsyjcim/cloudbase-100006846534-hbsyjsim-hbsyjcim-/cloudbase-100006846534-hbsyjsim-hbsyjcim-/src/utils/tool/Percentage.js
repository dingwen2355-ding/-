function Percentages (res) {
    // console.log('Percentages', res);
    const percentage =((Math.round((res * 10000)))/100.00).toFixed(2) + '%';
    return percentage
  }
  export { Percentages }
