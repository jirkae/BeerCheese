import React, { Component } from 'react';
import { Col } from 'reactstrap';

export default class Product extends Component {
  state = {
    name: null
  };

  componentWillMount() {
    //TODO call the rest to get details for given id
    this.setState({
      name: 'Product ' + this.props.productId
    });
  }

  render() {
    if(this.state.name){
      return(
        <Col lg="4" md="6" xs="12">
          <h2>{this.state.name}</h2>
        </Col>
      )
    }
  }
}