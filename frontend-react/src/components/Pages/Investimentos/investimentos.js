import React, {useEffect, useState} from 'react';
import InvestmentChart from './content/chart/charts';
import Accordion from './content/acordion/acordion';
import Detalhes from './content/details/detalhesInvestimentos';
import './investimentos.css';
import axios from "axios";
import {USER_TOKEN_REF} from "../../../constants/constants";

function Investimentos() {
  const [showDetalhes, setShowDetalhes] = useState(false);
  const [investments, setInvestments] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/investments/by-user-id', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        }).then((response) => {
            if (response.status === 200) {
                setInvestments(response.data)
            }
        }).catch((error) => {
            alert('Erro ao buscar investimentos ' + error)
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
      <Detalhes onClose={handleFecharDetalhes} investmentsList={investments} show={showDetalhes} />
    </div>
  );
}

export default Investimentos;