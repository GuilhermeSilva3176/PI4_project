import React, {useEffect, useState} from 'react';
import InvestmentChart from './content/chart/charts';
import Accordion from './content/acordion/acordion';
import Detalhes from './content/details/detalhesInvestimentos';
import './investimentos.css';
import axios from "axios";

function Investimentos() {
  const [showDetalhes, setShowDetalhes] = useState(false);
  const [investments, setInvestments] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/finances/by-user-id', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        }).then((response) => {
            if (response.status === 200) {
                setInvestments(response.data)
            }
        }).then(() => {
            setInvestments(investments?.filter((finance) => finance.type === "STOCK" || finance.type === "FIXED_INCOME"
                || finance.type === "FII"))
        }).catch((error) => {
            // alert('Erro ao buscar investimentos ' + error)
        })
    });

  const handleVerDetalhesClick = () => {
    setShowDetalhes(true);
  };

  const handleFecharDetalhes = () => {
    setShowDetalhes(false);
  };

  return (
    <div className='mainContent'>
      <Accordion handleVerDetalhesClick={handleVerDetalhesClick} investmentList={investments} />
      <hr className='verticalLine'></hr>
      <InvestmentChart investmentList={investments} />
      <Detalhes onClose={handleFecharDetalhes} show={showDetalhes} />
    </div>
  );
}

export default Investimentos;