import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';
import localizedTexts from '../../text_localization/LocalizedStrings';



export default class PackageOverviewDeliveryDetailsPage extends Component {
 render(){
    return (
      <Container>
        <PackageOverviewNav stage={3}/>
        <Row>
          PackageOverviewDeliveryDetailsPage
        </Row>
      </Container>
    );
  }
}
