import React, { useState, useEffect } from 'react';
import { Col, Row } from 'react-bootstrap';
import './InputComponent.css';
import PropTypes from 'prop-types';

const InputComponent = ({ label, inputType, value, onChange }) => {
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    const handleWindowResize = () => {
        setWindowWidth(window.innerWidth);
    };

    useEffect(() => {
        window.addEventListener('resize', handleWindowResize);

        return () => {
            window.removeEventListener('resize', handleWindowResize);
        };
    }, []);

    const Component = windowWidth <= 768 ? Row : Col;

    return (
        <Component>
            <div className="textboxCad">
                <p>{label}</p>
                <input type={inputType} value={value} onChange={onChange} required />
            </div>
        </Component>
    );
};

InputComponent.propTypes = {
    label: PropTypes.string.isRequired,
    inputType: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
};

export default InputComponent;