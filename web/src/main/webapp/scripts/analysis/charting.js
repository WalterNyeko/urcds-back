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
    return charting;
})();