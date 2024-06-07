import React, { useState, useEffect } from "react";
import Chart from "react-apexcharts";
import "./HomeLogged.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const HomeLogged = () => {
  const [saldoUsado, setSaldoUsado] = useState(0);
  const [saldoRestante, setSaldoRestante] = useState(0);
  const [financesList, setFinancesList] = useState([]);
  const [investmentsList, setInvestmentsList] = useState([]);
  const [authenticatedUser, setAuthenticatedUser] = useState(null);
  const [userIncome, setUserIncome] = useState(0);
  const [biggestExpense, setBiggestExpense] = useState(0);
  const [smallestExpense, setSmallestExpense] = useState(1000);
  const [biggestInvestment, setBiggestInvestment] = useState(0);
  const [smallestInvestment, setSmallestInvestment] = useState(1000);

  let navigate = useNavigate();

  useEffect(() => {
    // PEGAR O SALDO USADO E O SALDO RESTANTE DO USUÁRIO
    axios
      .get("http://localhost:8080/api/v1/finances/by-user-id")
      .then((response) => {
        if (response.status === 200) {
          setFinancesList(response.data);
          setAuthenticatedUser(response.data[0].user);
          response.data.reduce((acc, curr) => {
            if (curr.type === "EXPENSE") {
              setSaldoUsado((saldoUsado += curr.value));
            } else {
              setSaldoRestante((saldoRestante += curr.value));
            }
          }, 0);
          setUserIncome(
            response.data[0].user.income + saldoRestante - saldoUsado
          );
        }
        response.data.forEach((finance) => {
          if (finance.value > biggestExpense) {
            setBiggestExpense(finance.value);
          }
          if (finance.value < smallestExpense) {
            setSmallestExpense(finance.value);
          }
        });
      })
      .catch((error) => {
        console.log(error);
      });

    axios
      .get("http://localhost:8080/api/v1/investments/by-user-id")
      .then((response) => {
        if (response.status === 200) {
          setInvestmentsList(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const state = {
    series: [saldoUsado, saldoRestante],
    options: {
      colors: ["#700000", "#19a2fd"],

      chart: {
        foreColor: "#ffffff",
        type: "pie",
        fontSize: "18px",
      },
      labels: ["Saldo Usado", "Saldo Restante"],
      dataLabels: {
        enabled: true,
        style: {
          fontSize: "20px"
        },
      },
      legend: {
        position: "bottom",
        fontSize: "18px",
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200,
            },
          },
        },
      ],
    },
  };

  return (
    <div className="rootHomeLogged-div">
      <div className="title-div">
        <h1>
          Olá,{" "}
          {localStorage.getItem("user-name") !== null
            ? localStorage.getItem("user-name")
            : "usuário"}
          !
        </h1>
        <p>Seja bem-vindo ao nosso sistema de finanças pessoais.</p>
      </div>
      <div className="contentHomeLogged">
        <div className="pie-Saldo col-md-5 align-self-center">
          <Chart
            options={state.options}
            series={state.series}
            type="pie"
            width="100%"
          />
        </div>

        <div className="line-div"></div>

        <div className="container-homeLogged text-center col-md-5 align-self-center">
          <div className="row-HomeLogged-principal">
            <div className="contentGridSaldo">
              <h3>Saldo Projetado:</h3>
              <p>
                {userIncome < 0
                  ? "Você está no vermelho..."
                  : "Excelente, finanças em Dia!"}
              </p>
              <p>
                {userIncome >= 0 ? "R$ " + userIncome : "-R$ " + userIncome}
              </p>
            </div>
          </div>
          <div className="row-HomeLogged">
            <div className="col-HomeLogged-Plus">
              <div className="contentGrid">
                <h5>Maior Despesa</h5>
                <p>{biggestExpense}</p>
                <button onClick={navigate("/financas")}>
                  Acessar Finanças
                </button>
              </div>
            </div>
            <div className="col-HomeLogged-Minus">
              <div className="contentGrid">
                <h5>Menor Despesa</h5>
                <p>{smallestExpense}</p>
                <button onClick={navigate("/financas")}>
                  Acessar Finanças
                </button>
              </div>
            </div>
          </div>
          <div className="row-HomeLogged">
            <div className="col-HomeLogged-Plus">
              <div className="contentGrid">
                <h5>Maior Investimento</h5>
                <p>{biggestInvestment}</p>
                <button onClick={navigate("/investimentos")}>
                  Acessar Investimentos
                </button>
              </div>
            </div>
            <div className="col-HomeLogged-Minus">
              <div className="contentGrid">
                <h5>Menor Investimento</h5>
                <p>{smallestInvestment}</p>
                <button onClick={navigate("/investimentos")}>
                  Acessar Investimentos
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default HomeLogged;
