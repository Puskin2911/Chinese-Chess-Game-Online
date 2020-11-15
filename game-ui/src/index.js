import React from 'react';
import ReactDOM from 'react-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import './index.css';
import "font-awesome/css/font-awesome.min.css";
import $ from 'jquery';
import Popper from 'popper.js';
import "bootstrap/dist/js/bootstrap.min";
import App from './App';
import {BrowserRouter} from "react-router-dom";

ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);
