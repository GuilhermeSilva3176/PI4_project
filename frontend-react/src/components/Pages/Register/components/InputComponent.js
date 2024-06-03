import {Col} from "react-bootstrap";
import './InputComponent.css';
import PropTypes from "prop-types";

const InputComponent = ({label, inputType, value, onChange}) => {
    return (
        <Col>
            <div className="textboxCad">
                <p>{label}</p>
                <input type={inputType} value={value} onChange={onChange} required/>
            </div>
        </Col>
    )

}
InputComponent.propTypes = {
    label: PropTypes.string.isRequired,
    inputType: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired
}

export default InputComponent;