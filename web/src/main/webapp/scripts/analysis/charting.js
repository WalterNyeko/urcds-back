/**
 * Created by Frank on 3/18/15.
 */
var charting = (function() {
    var charting = Object.create(null);
    charting.createPieChart = function(tabulation, name, divId) {
        var chart = {};
        chart.type = 'pie';
        chart.name = name;
        chart.data = [];
        tabulation.attributeCounts.forEach(function (attr) {
            var attribute = [attr.name, attr.count];
            chart.data.push(attribute);
        });
        charting.reorderAndSlice(chart);
        new Highcharts.Chart(charting.createPieChartOptions(chart, divId));
    }
    charting.reorderAndSlice = function(chart) {
        var second = chart.data[1];
        var third = chart.data[2];
        if(second && third) {
            var newSecond = {
                name : second[0],
                y : second[1],
                sliced : true,
                selected : true
            };
            chart.data[1] = third;
            chart.data[2] = newSecond;
        }
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
        chart.subtitle = 'based on ' + crossTabulation.crashes.length + ' crashes';
        chart.categories = [];
        chart.series = [];
        crossTabulation.attributeCounts.forEach(function(xAttr) {
            chart.categories.push(xAttr.xName);
            xAttr.yAttributeCounts.forEach(function(yAttr) {
                var y = chart.series.filter(function(s) {return s.name === yAttr.yName})[0];
                if (y) {
                    y.data.push(yAttr.count);
                } else {
                    chart.series.push({ name: yAttr.yName, data: [yAttr.count]});
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
                    text: 'No. of Crashes'
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
    return charting;
})();