import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';
import localizedTexts from '../../text_localization/LocalizedStrings';



export default class PackageOverviewDelPayPage extends Component {
 render(){
    return (
      <Container>
        <PackageOverviewNav stage={2}/>
        <Row>
          PackageOverviewDelPayPage
        </Row>
      </Container>
    );
  }
}
