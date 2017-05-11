var labelChartVoid = function () {
    return;
};
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
        newRelicMonthly: 'newRelicMonthly',
        newRelicWeekly: 'newRelicWeekly'
    },
    url: {getDocuments: 'getDocuments',
        uploadFileUrl: 'parseFile'},
    defaultDocsNum: 1000,
    colors: {
        2015: '#ee3b3b', //brown2
        2016: '#66cdaa', //MediumAquamarine
        2017: '#ffb90f', //DarkGoldenrod1
        2018: '#8ee5ee', //CadetBlue2
        2019: '#b4eeb4'  //DarkSeaGreen2
    },
    chartType: {
        pageViewMillions: {
            metricName: 'EndUser',
            valueName: 'call_count',
            label: 'Page Views',
            optionsDaily: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000).toFixed(1);
                            return data + 'k';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000).toFixed(1) + 'k';
                                }
                            }
                        }
                    ],
                    xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: 'Week number'
                            }
                        }]
                }
            },
            optionsWeekly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return 'week '+ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000000).toFixed(2);
                            return data + 'M';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000000).toFixed(2) + 'M';
                                }
                            }
                        }
                    ],
                    xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: 'Week number'
                            }
                        }]
                }
            },
            optionsMonthly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            var year = data.datasets[tooltipItem.datasetIndex].label;
                            return ret+ ' '+year;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000000).toFixed(2);
                            return data + 'M';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000000).toFixed(1) + 'M';
                                }
                            }
                        }
                    ]
                }
            }
        },
        loadTime: {
            metricName: 'EndUser',
            valueName: 'average_response_time',
            label: 'Load Time',
            optionsDaily: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000).toFixed(1);
                            return data + ' sec.';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Seconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000).toFixed(1) + ' sec.';
                                }
                            }
                        }
                    ]
                }
            },
            optionsWeekly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return 'week '+ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000).toFixed(1);
                            return data + ' sec.';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {scaleLabel: {
                                display: true,
                                labelString: 'Seconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000).toFixed(1) + ' sec.';
                                }
                            }
                        }
                    ]
                }
            },
            optionsMonthly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            var year = data.datasets[tooltipItem.datasetIndex].label;
                            return ret+ ' '+year;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000).toFixed(1);
                            return data + ' sec.';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Seconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000).toFixed(1) + ' sec.';
                                }
                            }
                        }
                    ]
                }
            },
        },
        requestMillions: {
            metricName: 'HttpDispatcher',
            valueName: 'call_count',
            label: 'Server Requests',
            optionsDaily: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000000).toFixed(2);
                            return data + 'M';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000000).toFixed(2) + 'M';
                                }
                            }
                        }
                    ]
                }
            },
            optionsWeekly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return 'week '+ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000000).toFixed(1);
                            return data + 'M';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000000).toFixed(1) + 'M';
                                }
                            }
                        }
                    ]
                }
            },
            optionsMonthly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            var year = data.datasets[tooltipItem.datasetIndex].label;
                            return ret+ ' '+year;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            data = parseFloat(data/1000000).toFixed(1);
                            return data + 'M';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Millions'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label/1000000).toFixed(1) + 'M';
                                }
                            }
                        }
                    ]
                }
            },
        },
        responseTime: {
            metricName: 'HttpDispatcher',
            valueName: 'average_response_time',
            label: 'Server Response Time',
            optionsDaily: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            return data + ' ms';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Milliseconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label).toFixed(1) + ' ms';
                                }
                            }
                        }
                    ]
                }
            },
            optionsWeekly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            return 'week '+ret;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            return data + ' ms';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Milliseconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label).toFixed(1) + ' ms';
                                }
                            }
                        }
                    ]
                }
            },
            optionsMonthly: {
                tooltips: {
                    displayColors: false,
                    callbacks: {
                        beforeTitle: labelChartVoid,
                        title: labelChartVoid,
                        afterTitle: labelChartVoid,
                        beforeBody: labelChartVoid,
                        beforeLabel: function (tooltipItem, data) {
                            var ret = data.labels[tooltipItem.index];
                            var year = data.datasets[tooltipItem.datasetIndex].label;
                            return ret+ ' '+year;
                        },
                        label: function (tooltipItem, data) {
                            var data = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            return data + 'ms';
                        },
                        afterLabel: labelChartVoid,
                        afterBody: labelChartVoid,
                        beforeFooter: labelChartVoid,
                        footer: labelChartVoid,
                        afterFooter: labelChartVoid,
                        dataPoints: labelChartVoid
                    }
                },
                scales: {
                    yAxes: [
                        {
                            scaleLabel: {
                                display: true,
                                labelString: 'Milliseconds'
                            },
                            ticks: {
                                beginAtZero: true,
                                callback: function (label, index, labels) {
                                    return parseFloat(label).toFixed(1) + ' ms';
                                }
                            }
                        }
                    ]
                }
            }
        }
    }
};

