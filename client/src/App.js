import React from 'react';
import './App.css';
import { Grid } from '@mui/material';
import MainPage from './pages/MainPage';
import AccountBookPage from './pages/AccountBookPage';
import SettingPage from './pages/SettingPage';
import {BrowserRouter as Router, Routes , Route} from "react-router-dom"

function App() {
  return (
    
    <Grid className="App">
      <Router>
        <Routes>
          <Route path="/" element={<MainPage/>}/>
          <Route path="/accountbook" element={<AccountBookPage/>}/>
          <Route path="/setting" element={<SettingPage/>}/>
        </Routes>
      </Router>
    </Grid>
  );
}

export default App;
