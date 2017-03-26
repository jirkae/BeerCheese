import React from 'react';
import { Link } from 'react-router';
import { Row, Col, Button, NavLink, NavItem, Nav } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';

export default props => (
  <Row>
    <Col xl="2" lg="2" md="2" sm="2" xs="3">
      <Button>{localizedTexts.PackageCreationNav.back}</Button>
    </Col>
    <Col xl="8s" lg="8" md="8" sm="8" xs="6">
      <Nav>
        <NavItem>
          <NavLink tag={Link} to="/create_package_beer">
            {localizedTexts.PackageCreationNav.beer}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/create_package_supplement">
            {localizedTexts.PackageCreationNav.supplements}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/create_package_package">
            {localizedTexts.PackageCreationNav.packages}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/create_package_message">
            {localizedTexts.PackageCreationNav.message}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/create_package_summary">
            {localizedTexts.PackageCreationNav.summary}
          </NavLink>
        </NavItem>
      </Nav>
    </Col>
    <Col xl="2" lg="2" md="2" sm="2" xs="3">
      <Button>{localizedTexts.PackageCreationNav.next}</Button>
    </Col>
  </Row>
);
