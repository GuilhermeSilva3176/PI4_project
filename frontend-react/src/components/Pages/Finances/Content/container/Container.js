import React from "react";
import './Container.css';
import FinanceTable from "../financetable/FinanceTable";
import PropTypes from "prop-types";

function Container({handleSeeDetailsClick, financeList}) {
    return (
        <div id="finance_container" className="container">
            <div className="row justify-content-between">
                <div className="col-md-5">
                    <FinanceTable tableName={"Despesas"} financeListFiltered={
                        financeList && financeList.filter((finance) => finance.type === "EXPENSE")
                    }/>
                </div>
                <div className="col-md-2 d-flex justify-content-center">
                    <button className="btndetalhes" onClick={handleSeeDetailsClick}>Detalhes</button>
                </div>
                <div className="col-md-5">
                    <FinanceTable tableName={"Renda"} financeListFiltered={
                        financeList && financeList.filter((finance) => finance.type === "INCOME")
                    }/>
                </div>
            </div>
        </div>
    );
}
Container.propTypes = {
    handleSeeDetailsClick: PropTypes.func.isRequired,
    financeList: PropTypes.array.isRequired
}
 
export default Container;
