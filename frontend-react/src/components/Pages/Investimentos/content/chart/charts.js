import React, { Component } from 'react';
import Chart from 'react-apexcharts'
import './chart.css';

class Donut extends Component {

  constructor(props) {
    super(props);

    this.state = {
      series: [50, 25, 25],
      options: {
        colors: ['#7010ff', '#66DA26', '#54D4ff'],

        chart: {
          foreColor: '#ffffff',
          type: 'pie'

        },
        labels: ['Ações', 'FIIS', 'Renda Fixa'],
        legend: {
          position: 'bottom',
          horizontalAlign: 'left',
          fontSize: '18px'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            }
          }
        }]
      },
    }
  }

  render() {

    return (
      <div className="pie">
        <Chart options={this.state.options} series={this.state.series} type="pie" width="680" />
      </div>
    );
  }
}

export default Donut;