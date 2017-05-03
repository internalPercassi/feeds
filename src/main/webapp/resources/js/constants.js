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
        newRelicMonthly:'newRelicMonthly'
    },
    url: {getDocuments: 'getDocuments',
        uploadFileUrl: 'parseFile'},
    defaultDocsNum: 1000

};