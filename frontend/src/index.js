import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import {configureStore} from "./store/configureStore";

const store = configureStore()

ReactDOM.render(
    <App store={store}/>,
    document.getElementById('root')
);
