import React, { Component } from 'react';
import {
  Navbar,
  NavbarToggler,
  Collapse,
  Nav,
  NavItem,
  NavLink,
  NavbarBrand,
  Button
} from 'reactstrap';
import { Link } from 'react-router';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { css } from 'glamor';
import Cart from './Cart';

const BEER_IMG_URL = 'https://img.clipartfox.com/c6c3f93fcfdd38440d093b3140604408_beer-free-to-use-clipart-beer-clipart-transparent-background_985-1280.png';

export default class NavBar extends Component {
  state = {
    isOpen: false
  };

  toggle = () => {
    this.setState({
      isOpen: !this.state.isOpen
    });
  };

  render() {
    return (
      <Navbar light toggleable>
        <NavbarBrand tag={Link} to="/">
          <h3>
            <img
              src={BEER_IMG_URL}
              alt="beerIcon"
              className={`${this.cssBeerIcon}`}
            />
            {' '}
            Pivní suvenýry
          </h3>
        </NavbarBrand>
        <NavbarToggler right onClick={this.toggle} />
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto mr-auto" navbar>
            <NavItem>
              <NavLink tag={Link} to="/about_us">
                <Button color="secondary">{localizedTexts.NavBar.aboutUs}</Button>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/conditions">
                <Button color="secondary">{localizedTexts.NavBar.terms}</Button>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/contact">
                <Button color="secondary">{localizedTexts.NavBar.contact}</Button>
              </NavLink>
            </NavItem>
          </Nav>
        </Collapse>
        <Cart />
      </Navbar>
    );
  }

  cssBeerIcon = css({
    height: '70px',
    width: '70px'
  });
}
