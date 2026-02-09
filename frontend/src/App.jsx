import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/NavBar';
import MonsterList from './components/MonsterList';
import HunterForm from './components/HunterForm';
import QuestList from './components/QuestList';
import HunterList from './components/HunterList';

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      
      <main className="container" style={{ paddingTop: '20px' }}>
        <Routes>
          <Route path="/" element={<MonsterList />} />
          <Route path="/monsters" element={<MonsterList />} />
          <Route path="/quests" element={<QuestList />} />
          <Route path="/hunters/new" element={<HunterForm />} />
          <Route path="/hunters" element={<HunterList />} />

        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;