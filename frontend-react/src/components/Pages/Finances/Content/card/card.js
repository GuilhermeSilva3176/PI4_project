import React from "react";
import './card.css';

function Card({ handleVerDetalhesClick }) {
  const despesas = [
    { nome: "Aluguel", valor: 1000, tipo: "Despesa" },
    { nome: "Comida", valor: 500, tipo: "Despesa" },
  ];

  const renda = [
    { nome: "Salário", valor: 2000, tipo: "Renda" }
  ];

  return (
    <div id="finance_container" className="container">
      <div className="row justify-content-between">
        <div className="col-md-5">
          <div className="card card-custom">
            <div className="card-header card-header-custom text-center">
              Despesas
            </div>
            <div className="card-body card-body-custom">
              <table className="table table-valores">
                <thead>
                  <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Tipo</th>
                  </tr>
                </thead>
                <tbody>
                  {despesas.map((despesa, index) => (
                    <tr key={index}>
                      <td>{despesa.nome}</td>
                      <td>{despesa.valor}</td>
                      <td>{despesa.tipo}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            <div className="card-footer card-footer-custom">
            </div>
          </div>
        </div>
        <div className="col-md-2 d-flex justify-content-center">
          <button className="btndetalhes" onClick={handleVerDetalhesClick}>Detalhes</button>
        </div>
        <div className="col-md-5">
          <div className="card card-custom">
            <div className="card-header card-header-custom text-center">
              Rendas
            </div>
            <div className="card-body card-body-custom">
              <table className="table table-valores">
                <thead>
                  <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Tipo</th>
                  </tr>
                </thead>
                <tbody>
                  {renda.map((rendaItem, index) => (
                    <tr key={index}>
                      <td>{rendaItem.nome}</td>
                      <td>{rendaItem.valor}</td>
                      <td>{rendaItem.tipo}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            <div className="card-footer card-footer-custom">
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Card;
