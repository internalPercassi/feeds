var appConstants = {
    getDocUrl: 'getDocuments',
    uploadFileUrl: 'parseFile',
    app: $.sammy.apps['#app'],
    collectionNames: {
        GL: "GL",
        OS: "OS",
        FacebookProduct: 'FacebookProduct',
        UploadedFile: 'uploadedFile',
        newRelicDaily: 'newRelicDaily',
        newRelicMonthly: 'newRelicMonthly'
    },
    url: {getDocuments: 'getDocuments',
        uploadFileUrl: 'parseFile'},
    defaultDocsNum: 1000,
    colors: {
        2015: '#ee3b3b', //brown2
        2016: '#66cdaa',  //MediumAquamarine
        2017: '#ffb90f',  //DarkGoldenrod1
        2018: '#8ee5ee',  //CadetBlue2
        2019: '#b4eeb4'  //DarkSeaGreen2
    },
    chartType:{
        pageViewMillions:{
            metricName:'EndUser',
            valueName:'callCount',
            label:'Page Views'
        },
        loadTime:{
            metricName:'EndUser',
            valueName:'averageResponseTime',
            label:'Load Time'
        },
        requestMillions:{
            metricName:'HttpDispatcher',
            valueName:'callCount',
            label:'Server Requests'
        },
        responseTime:{
            metricName:'HttpDispatcher',
            valueName:'averageResponseTime',
            label:'Server Response Time'
        }
    }
};