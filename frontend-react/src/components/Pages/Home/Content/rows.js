import React from "react";
import './rows.css';

function Rows() {
    return (
        <div className="rows">
            <section id="row1" className="row">
                <div className="col-md-6">
                    <img src="/images/second_home_image.svg" alt="Presentation" className="img-fluid"/>
                </div>
                <div className="col-md-5 align-self-center">
                    <h2 className="titleRow">Economizar Dinheiro</h2>
                    <hr/>

                    <p>
                        Economizar dinheiro é essencial para garantir estabilidade financeira e alcançar objetivos de
                        longo prazo. Para isso, é fundamental estabelecer metas financeiras claras que motivem sua
                        economia. Criar e seguir um orçamento mensal ajuda a controlar as finanças e identificar áreas
                        onde é possível cortar gastos desnecessários.
                    </p>
                </div>
            </section>
            <section id="row2" className="row">
                <div className="col-md-5 align-self-center">
                    <h2 className="titleRow">Diversificação de Carteira</h2>
                    <hr/>
                    <p>
                        A diversificação de carteira é um princípio fundamental no mundo dos investimentos. Consiste na
                        alocação de recursos em diferentes classes de ativos para reduzir o risco e maximizar os
                        retornos a longo prazo. Essa estratégia baseia-se na ideia de que diferentes tipos de
                        investimentos têm comportamentos distintos em diferentes condições de mercado.
                    </p>
                </div>
                <div className="col-md-6">
                    <img src="/images/third_home_image.svg" alt="Presentation" className="img-fluid"/>
                </div>
            </section>
        </div>
    );
}

export default Rows;