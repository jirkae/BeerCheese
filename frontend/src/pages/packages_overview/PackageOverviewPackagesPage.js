import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';
import localizedTexts from '../../text_localization/LocalizedStrings';



export default class PackageOverviewPackagesPage extends Component {
 render(){
    return (
      <Container>
        <PackageOverviewNav stage={1}/>
        <Row>
          PackageOverviewPackagesPage
        </Row>
      </Container>
    );
  }
}
