/**
 * Created by Frank on 3/18/15.
 */
var charting = (function() {
    var charting = Object.create(null);
    charting.createPieChart = function(tabulation, name, divId) {
        var chart = {};
        chart.type = 'pie';
        chart.name = name;
        chart.subtitle = 'based on ' + tabulation.totalUnits() + ' ' + pluralize(tabulation.units());
        chart.data = [];
        tabulation.attributeCounts.map(function (attr) {
            var attribute = [attr.name.toString(), attr.count];
            chart.data.push(attribute);
        });
        new Highcharts.Chart(charting.createPieChartOptions(chart, divId));
    }
    charting.createPieChartOptions = function(chart, divId) {
        var options = {
            chart: {
                renderTo: divId,
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: chart.name
            },
            subtitle: {
                text: chart.subtitle
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 2)
                        + ' %';
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>: '
                                + Highcharts.numberFormat(this.percentage, 2) + ' %';
                        },
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    showInLegend: true
                }
            },
            legend : {
              labelFormatter: function() {
                  return this.name + ' (' + Highcharts.numberFormat(this.percentage, 2) + ' %)';
              }
            },
            series: [chart]
        };
        return options;
    }
    charting.createBarChart = function(crossTabulation, title, divId) {
        var chart = {};
        chart.type = 'column';
        chart.title = title;
        chart.subtitle = 'based on ' + crossTabulation.totalUnits() + ' ' + pluralize(crossTabulation.units());
        chart.categories = [];
        chart.series = [];
        crossTabulation.attributeCounts.map(function(xAttr) {
            chart.categories.push(xAttr.xName.toString());
            xAttr.yAttributeCounts.map(function(yAttr) {
                var y = chart.series.filter(function(s) {return s.name === yAttr.yName})[0];
                if (y) {
                    y.data.push(yAttr.count);
                } else {
                    chart.series.push({
                        data: [yAttr.count],
                        name: yAttr.yName.toString(),
                        unit: util.capitalizeFirst(pluralize(crossTabulation.units()))
                    });
                }
            });
        });
        new Highcharts.Chart(charting.createBarChartOptions(chart, divId));
    }
    charting.createBarChartOptions = function(chart, divId) {
        var options = {
            chart: {
                type: chart.type,
                renderTo: divId,
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text : chart.title
            },
            subtitle: {
                text: chart.subtitle
            },
            xAxis : {
                categories: chart.categories,
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'No. of ' + chart.series[0].unit
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                columns: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: chart.series
        };
        return options;
    }
    charting.createColumnChart = function(tabulation, title, divId) {
        var chart = {};
        chart.type = 'column';
        chart.title = title;
        chart.subtitle = 'based on ' + tabulation.totalUnits() + ' ' + pluralize(tabulation.units());
        chart.series = [];
        chart.data = [];
        tabulation.attributeCounts.map(function (attr) {
            chart.data.push([attr.name.toString(), attr.count]);
        });
        chart.dataLabels = {
            enabled: true,
            rotation: -90,
            color: '#FFFFFF',
            align: 'right',
            format: '{point.y:.0f}', // no decimal
            y: 10, // 10 pixels down from the top
            style: {
            fontSize: '13px',
            fontFamily: 'Verdana, sans-serif'
            }
        };
        chart.series.push({
            name: util.capitalizeFirst(pluralize(tabulation.units())),
            data: chart.data,
            dataLabels: chart.dataLabels
        });
        new Highcharts.Chart(charting.createColumnChartOptions(chart, divId));
    }
    charting.createColumnChartOptions = function(chart, divId) {
        var options = {
            chart: {
                type: chart.type,
                renderTo: divId,
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text : chart.title
            },
            subtitle: {
                text: chart.subtitle
            },
            xAxis : {
                type: 'category',
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, san-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'No. of ' + chart.series[0].name
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            series: chart.series
        };
        return options;
    }
    charting.createTrendLineGraph = function(crashTrend, categories, title, divId) {
        var chart = {};
        chart.title = title;
        chart.subtitle = 'based on ' + crashTrend.totalUnits() + ' ' + pluralize(crashTrend.attributeType);
        chart.categories = categories;
        chart.series = [];
        crashTrend.attributeCounts.map(function(xAttr) {
            xAttr.yAttributeCounts.map(function(yAttr) {
                var y = chart.series.filter(function(s) { return s.name === yAttr.yName })[0];
                if (y) {
                    y.data.push(yAttr.count);
                } else {
                    chart.series.push({
                        name: yAttr.yName.toString(),
                        data: [yAttr.count],
                        unit: pluralize(util.capitalizeFirst(crashTrend.attributeType))
                    });
                }
            });
        });
        new Highcharts.Chart(charting.createLineGraphOptions(chart, divId));
    }
    charting.createLineGraphOptions = function(chart, divId) {
        var options = {
            chart: {
                renderTo: divId,
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text : chart.title,
                x: -20
            },
            subtitle: {
                text: chart.subtitle,
                x: -20
            },
            xAxis : {
                categories: chart.categories
            },
            yAxis: {
                title: {
                    text: 'No. of ' + chart.series[0].unit
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: chart.series
        };
        return options;
    }
    return charting;
})();