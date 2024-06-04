import React, {useEffect, useState} from 'react';
import Header from "../../Global/header";
import Detalhes from "./Content/details/detalhes";
import Container from "./Content/container/Container";
import './style.css';
import axios from "axios";

const Finances = () => {
  const [showDetalhes, setShowDetalhes] = useState(false);
  const [finances, setFinances] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/finances/by-user-id', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        }).then((response) => {
            if (response.status === 200) {
                setFinances(response.data)
            }
        }).then(() => {
            setFinances(finances?.filter((finance) => finance.type === "INCOME" || finance.type === "EXPENSE"))
        }).catch((error) => {
            alert('Erro ao buscar finanças ' + error)
        })
    }, []);

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
        <Container handleSeeDetailsClick={handleVerDetalhesClick} financeList={finances}/>
        <Detalhes onClose={handleFecharDetalhes} show={showDetalhes} financeList={
            finances?.toSorted((a, b) => a.type === "EXPENSE" ? -1 : 1)
        }/>
      </div>
    </div>
  );
}

export default Finances;
