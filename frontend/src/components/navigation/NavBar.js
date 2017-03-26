import React, { Component } from 'react';
import {
  Navbar,
  NavbarToggler,
  Collapse,
  Nav,
  NavItem,
  NavLink,
  NavbarBrand
} from 'reactstrap';
import { Link } from 'react-router';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';
import { css } from 'glamor';

const BEER_IMG_URL = 'https://img.clipartfox.com/c6c3f93fcfdd38440d093b3140604408_beer-free-to-use-clipart-beer-clipart-transparent-background_985-1280.png';

class NavBar extends Component {
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
      <Navbar color="faded" light toggleable>
        <NavbarBrand tag={Link} to="/">
          <h3><img src={BEER_IMG_URL} alt="beerIcon" className={`${this.cssBeerIcon}`}/> Pivní suvenýry</h3>
        </NavbarBrand>
        <NavbarToggler right onClick={this.toggle} />
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto" navbar>
            <NavItem>
              <NavLink tag={Link} to="/about_us">{localizedTexts.NavBar.aboutUs}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/conditions">{localizedTexts.NavBar.terms}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/contact">{localizedTexts.NavBar.contact}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/">{localizedTexts.NavBar.mainPage}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/package_overview_packages">{localizedTexts.NavBar.packagesOverview}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/create_package_beer">{localizedTexts.NavBar.createPackage}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="#" onClick={() => this.props.openModal({name:'logIn',data:null})}>{localizedTexts.NavBar.logIn}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/register">{localizedTexts.NavBar.signUp}</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="#" onClick={() => console.log('log out')}>{localizedTexts.NavBar.logOut}</NavLink>
            </NavItem>
          </Nav>
        </Collapse>
      </Navbar>
    );
  }

  cssBeerIcon = css({
    'height': '70px',
    'width': '70px'
  });
}

export default connect(
  null,
  {
    openModal
  }
)(NavBar)
