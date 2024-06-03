import React, { useState } from 'react';
import Chart from './content/chart/charts';
import Accordion from './content/acordion/acordion';
import Detalhes from './content/details/detalhesInvestimentos'; 
import './investimentos.css';

function Investimentos() {
  const [showDetalhes, setShowDetalhes] = useState(false);

  const handleVerDetalhesClick = () => {
    setShowDetalhes(true);
  };

  const handleFecharDetalhes = () => {
    setShowDetalhes(false);
  };

  return (
    <div className='mainContent'>
      <Accordion handleVerDetalhesClick={handleVerDetalhesClick}/>
      <hr className='verticalLine'></hr>
      <Chart/>
      <Detalhes onClose={handleFecharDetalhes} show={showDetalhes} />
    </div>
  );
}

export default Investimentos;