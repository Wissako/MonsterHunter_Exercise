import { useEffect, useState } from 'react';

export default function MonsterList() {
  const [monsters, setMonsters] = useState([]);
  const [loading, setLoading] = useState(true);

  // Cargar datos al iniciar
  useEffect(() => {
    fetch('http://localhost:8080/monsters')
      .then(res => res.json())
      .then(data => {
        setMonsters(data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error conectando:", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p aria-busy="true">Cargando bestiario...</p>;

  return (
    <section>
      <h2>🐾 Bestiario</h2>
      <div className="monster-grid">
        {monsters.map(m => (
          <article className='monster-card' key={m.id}>
            <header>
                <img src={m.imageUrl} alt={m.name} style={{ width: '100%', height: 'auto', borderRadius:'8px' }} />
              <strong>{m.name}</strong>
              <span style={{ float: 'right' }}>⭐ {m.threatLevel}</span>
            </header>
            <p><strong>Tipo:</strong> {m.type}</p>
            <p><strong>Debilidad:</strong> <span style={{ color: '#e53935' }}>{m.weakness}</span></p>
            <p><strong>Hábitats:</strong><small>{m.habitats?.join(', ') || 'Desconocido'}</small></p>
          </article>
        ))}
      </div>
    </section>
  );
}