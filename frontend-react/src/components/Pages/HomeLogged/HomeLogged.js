import './HomeLogged.css'

const HomeLogged = () => {
    return (
        <div className="root-div">
            <h1>Olá, {localStorage.getItem('user-name') !== null ? localStorage.getItem('user-name') : 'usuário'}!</h1>
            <p>Seja bem-vindo ao nosso sistema de finanças pessoais.</p>

            <div className="row">
                <div className="col">
                    <div className="card">
                        <h2>Saldo</h2>
                        <p>R$ 0,00</p>
                    </div>
                </div>
                <div className="col">
                    <div className="card">
                        <h2>Despesas</h2>
                        <p>R$ 0,00</p>
                    </div>
                </div>
                <div className="col">
                    <div className="card">
                        <h2>Receitas</h2>
                        <p>R$ 0,00</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HomeLogged;