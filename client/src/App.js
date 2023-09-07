import React from 'react';
import './App.css';
import { Grid } from '@mui/material'; //mui에 의한 의존성 문제 해결!
import MainPage from './pages/MainPage';
import AccountBookPage from './pages/AccountBookPage';
import SettingPage from './pages/SettingPage';
import FeadbackPage from './pages/FeadbackPage';
import AccountBookDetailPage from './pages/AccountBookDetailPage'
import DiaryDetailPage from './pages/DiaryDetailPage';
import {BrowserRouter as Router, Routes , Route} from "react-router-dom";

function App() {
  return (
    <Grid className="App">
      <Router>
        <Routes>
          <Route path="/" element={<MainPage/>}/>
          <Route path="/accountbook" element={<AccountBookPage/>}/>
          <Route path="/setting" element={<SettingPage/>}/>
          <Route path="/feadback" element={<FeadbackPage/>}/>
          <Route path ="/accountbook/:id" element={<AccountBookDetailPage/>}/>
          <Route path ="/diary/:id" element={<DiaryDetailPage/>}/>
        </Routes>
      </Router>
    </Grid>
  );
}

export default App;
