import React from "react";
import "./App.css";
import { Grid } from "@mui/material"; //mui에 의한 의존성 문제 해결!
import MainPage from "./pages/MainPage";
import AccountBookPage from "./pages/AccountBookPage";
import SettingPage from "./pages/SettingPage";
import DiaryPage from "./pages/DiaryPage";
import AccountBookDetailPage from "./pages/AccountBookDetailPage";
import DiaryDetailPage from "./pages/DiaryDetailPage";
import DiaryWritePage from './pages/DiaryWritePage';
import SignUpPage from "./pages/SignUpPage";
import FixedExpenditurePage from "./pages/FixedExpenditurePage";
import FutureExpenditurePage from "./pages/FutureExpenditurePage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignInPage from "./pages/SignInPage";
import TotalDiaryPage from "./pages/TotalDiaryPage";

function App() {
  return (
    <Grid className="App">
      <Router>
        <Routes>
          <Route path="/" element={<SignInPage />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/mainpage" element={<MainPage />} />
          <Route path="/accountbook" element={<AccountBookPage />} />
          <Route path="/setting" element={<SettingPage />} />
          <Route path="/diary" element={<DiaryPage />} />
          <Route path="/accountbook/:id" element={<AccountBookDetailPage />} />
          <Route path="/diary/:id" element={<DiaryDetailPage />} />
          <Route path="/diary/write" element={<DiaryWritePage />} />
          <Route path="/fixed" element={<FixedExpenditurePage />} />
          <Route path="/future" element={<FutureExpenditurePage />} />
          <Route path="/diary/total" element={ <TotalDiaryPage/>} />
        </Routes>
      </Router>
    </Grid>
  );
}

export default App;
