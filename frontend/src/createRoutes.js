import React from "react";
import {IndexRoute, Route} from "react-router";
import {AppPage} from "./App.js";
import HomePage from "./pages/HomePage.js";
import PageNotFound from "./pages/PageNotFound.js";

export function createRoutes(store) {

    const requireAuth = (nextState, replace) => {
        const { user } = store.getState();
        if ( !user ) {
            replace('/');
        }
    };

    return (
        <Route path="/" component={AppPage}>
            <IndexRoute component={HomePage}/>
            {/*<Route path="/messages" component={MessagesPage} onEnter={requireAuth}/>*/}
            <Route path='*' component={PageNotFound} />
        </Route>
    );
}
