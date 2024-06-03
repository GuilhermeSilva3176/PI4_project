import React, { useState } from 'react';
import Header from "../../Global/header";
import Detalhes from "./Content/details/detalhes";
import Card from "./Content/card/card";
import './style.css';

function Finances( ){
  const [showDetalhes, setShowDetalhes] = useState(false);

  const handleVerDetalhesClick = () => {
    setShowDetalhes(true);
  };

  const handleFecharDetalhes = () => {
    setShowDetalhes(false);
  };


  return (
    <div id="page-content">
      <Header/>
      <div className="page-content">
        <div className="container">
            <h2 className="financesTitle">Suas Finanças</h2>
        </div>
        <Card handleVerDetalhesClick={handleVerDetalhesClick}/>
        <Detalhes onClose={handleFecharDetalhes} show={showDetalhes} />
      </div>
    </div>
  );
}

export default Finances;
