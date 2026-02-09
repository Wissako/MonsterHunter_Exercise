import { useEffect, useState } from 'react';

export default function QuestList() {
  const [quests, setQuests] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/quests')
      .then(res => res.json())
      .then(setQuests)
      .catch(console.error);
  }, []);

  return (
    <section>
      <h2>📜 Tablón de Misiones</h2>
      {quests.map(q => (
        <article key={q.id}>
          <header>{q.name}</header>
          <p><strong>Objetivo:</strong> {q.targetMonsterName}</p>
          <p><strong>Dificultad:</strong> {q.difficulty} ⭐</p>
          <p><strong>Recompensa:</strong> {q.reward}z 💰</p>
        </article>
      ))}
    </section>
  );
}