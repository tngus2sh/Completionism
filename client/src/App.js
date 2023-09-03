import React from 'react';
import './App.css';
import {Grid} from '@material-ui/core' 
import MainPage from './pages/MainPage';
import AccountBookPage from './pages/AccountBookPage';
import {BrowserRouter as Router, Routes , Route} from "react-router-dom"

function App() {
  return (
    
    <Grid className="App">
      <Router>
        <Routes>
          <Route path="/" element={<MainPage/>}/>
          <Route path="/accountbook" element={<AccountBookPage/>}/>
        </Routes>
      </Router>
    </Grid>
  );
}

export default App;
