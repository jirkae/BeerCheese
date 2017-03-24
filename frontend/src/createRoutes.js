import React from 'react';
import { IndexRoute, Route } from 'react-router';

import HomePage from './pages/HomePage';
import PageNotFound from './pages/PageNotFound';
import RootPage from './pages/RootPage';
import LoginPage from './pages/LoginPage';
import PackageCreationPage from './pages/PackageCreationPage';
import RegistrationPage from './pages/RegistrationPage';
import ShoppingCartPage from './pages/ShoppingCartPage';
import ContactPage from './pages/ContactPage';
import BusinessConditionsPage from './pages/BusinessConditionsPage';

export function createRoutes(store) {
  // const requireAuth = (nextState, replace) => {
  //   const { user } = store.getState();
  //   if (!user) {
  //     replace('/');
  //   }
  // };

  return (
    <Route path="/" component={RootPage}>
      <IndexRoute component={HomePage} />
      <Route path="/login" component={LoginPage} />
      <Route path="/register" component={RegistrationPage} />
      <Route path="/createPackage" component={PackageCreationPage} />
      <Route path="/cart" component={ShoppingCartPage} />
      <Route path="/contact" component={ContactPage} />
      <Route path="/conditions" component={BusinessConditionsPage} />
      {/* <Route path="/messages" component={MessagesPage} onEnter={requireAuth}/>*/}
      <Route path="*" component={PageNotFound} />
    </Route>
  );
}
