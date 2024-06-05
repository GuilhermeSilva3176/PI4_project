import React, {useEffect, useState} from 'react';
import Chart from 'react-apexcharts'
import './chart.css';
import PropTypes from "prop-types";

const InvestmentChart = ({investmentList}) => {
    const [hiddenChart, setHiddenChart] = useState(false)
    const getInvestmentValueSumByType = ({investmentType}) => {
        return investmentList?.size > 0 ? investmentList?.filter((investment) => investment.type === investmentType)
            .map((investment) => investment.value).reduce((a, b) => a + b, 0) : 0
    }

    const stocks = getInvestmentValueSumByType({investmentType: "STOCK"})
    const fiis = getInvestmentValueSumByType({investmentType: "FII"})
    const fixedIncome = getInvestmentValueSumByType({investmentType: "FIXED_INCOME"})

    useEffect(() => {
        if (stocks + fiis + fixedIncome === 0) {
            setHiddenChart(true)
        }
    }, [stocks, fiis, fixedIncome]);

    const state = {
        series: [stocks, fiis, fixedIncome],
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