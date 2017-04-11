import React from 'react';
import { Jumbotron } from 'reactstrap';
import { Link } from 'react-router';
import { Row, Col } from 'reactstrap';
import localizedStrings from '../../text_localization/LocalizedStrings';
import { css } from 'glamor';
import CartContent from '../CartContent';
import FontAwesome from 'react-fontawesome';

export default class Cart extends React.Component {
  state = {
    price: 9000,
    amount: 3,
    showCartContent: false
  };

  render() {
    return (
      <Col xs={2}>
        <Jumbotron className={`${this.cssNavPanel}`}>
          <Row>
            <Col xs="7">
              <ul className={`${this.cssCartControl}`}>
                <li>
                  {localizedStrings.NavPanel.amount}: {this.state.amount} ks
                </li>
                <li>
                  {localizedStrings.NavPanel.price}: {this.state.price} Kč
                </li>
              </ul>
            </Col>
            <Col xs="5">
              <Link
                to="/package-overview"
                className={`${this.cssExpandLink}`}
                onClick={() => {
                    return;
                    this.setState({
                      showCartContent: !this.state.showCartContent
                    })}}
              >
                <FontAwesome name="shopping-cart" style={{fontSize: '45px'}}/>
              </Link>
            </Col>
          </Row>
        </Jumbotron>
        {this.state.showCartContent ? <CartContent /> : ''}
      </Col>
    );
  }

  cssNavPanel = css({
    margin: 0,
    padding: '5px',
    height: '70px'
  });

  cssCartControl = css({
    listStyleType: 'none',
    minWidth: '140px',
    paddingLeft: '20px',
    paddingTop: '5px'
  });

  cssExpandLink = css({
    textDecoration: 'none',
    color: 'black'
  });
}
