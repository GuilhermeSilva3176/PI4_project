import React, {useEffect, useState} from 'react';
import Chart from 'react-apexcharts';
import './chart.css';
import PropTypes from "prop-types";

const InvestmentChart = ({investmentList}) => {
    const [hiddenChart, setHiddenChart] = useState(false)

    const [stocks, setStocks] = useState(0);
    const [fiis, setFiis] = useState(0);
    const [fixedIncome, setFixedIncome] = useState(0);

    useEffect(() => {
        setStocks(investmentList.filter(investment => investment.type === "STOCK")
            .reduce((acc, investment) => acc + (investment.price * investment.quantity), 0))
        setFiis(investmentList.filter(investment => investment.type === "FII")
            .reduce((acc, investment) => acc + (investment.price * investment.quantity), 0))
        setFixedIncome(investmentList.filter(investment => investment.type === "FIXED_INCOME")
            .reduce((acc, investment) => acc + (investment.price * investment.quantity), 0))
        if (stocks + fiis + fixedIncome === 0) {
            setHiddenChart(true)
        } else {
            setHiddenChart(false)
        }
    }, [investmentList]);

    const state = {
        series: [stocks, fiis, fixedIncome],
        options: {
            colors: ['#7010ff', '#66DA26', '#54D4ff'],

            chart: {
                foreColor: '#ffffff',
                type: 'pie',
                fontSize: '18px'
            },
            labels: ['Ações', 'FIIS', 'Renda Fixa'],
            dataLabels: {
                enabled: true,
                style: {
                    fontSize: '18px'
                }
            },
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
    return (
        <div className="pie">
            {hiddenChart ? <h2>Não há dados para serem exibidos.</h2>
                : <Chart options={state.options} series={state.series} type="pie" width="680" />
            }
        </div>
    )
}
InvestmentChart.propTypes = {
    investmentList: PropTypes.array.isRequired
}

export default InvestmentChart;