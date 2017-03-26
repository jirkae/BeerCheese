import React from 'react';
import { Jumbotron } from 'reactstrap';
import { Link } from 'react-router';
import { Row, Col } from 'reactstrap';
import localizedStrings from '../../text_localization/LocalizedStrings';
import { css } from 'glamor';
import CartContent from '../CartContent';

export default class NavPanel extends React.Component {
  state = {
    price: 9000,
    amount: 3,
    showCartContent: false
  };

  render() {
    return (
      <div>
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
                to="#"
                className={`${this.cssExpandLink}`}
                onClick={() =>
                  this.setState({
                    showCartContent: !this.state.showCartContent
                  })}
              >
                <i className="fa fa-expand fa-3" />
              </Link>
            </Col>
          </Row>
        </Jumbotron>
        {this.state.showCartContent ? <CartContent /> : ''}
      </div>
    );
  }

  cssNavPanel = css({
    margin: 0,
    padding: '5px',
    height: '70px'
  });

  cssCartControl = css({
    'list-style-type': 'none',
    'min-width': '140px',
    'padding-left': '20px',
    'padding-top': '5px'
  });

  cssExpandLink = css({
    textDecoration: 'none',
    color: 'black'
  });
}
