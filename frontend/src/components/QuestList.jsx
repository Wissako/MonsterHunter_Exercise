import { useEffect, useState } from 'react';

export default function QuestList() {
  const [quests, setQuests] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/quests')
      .then(res => res.json())
      .then(data => setQuests(data))
      .catch(err => console.error("Error cargando misiones:", err));
  }, []);

  if (quests.length === 0) return <p style={{textAlign: 'center', marginTop: '20px', color: 'white'}}>📭 No hay misiones en el tablón.</p>;

  return (
    <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', // Mismo tamaño que Monsters
        gap: '25px',
        padding: '20px'
    }}>
      {quests.map(q => (
        <article key={q.id} style={{
            border: '2px solid #5d4037', // Borde marrón estilo MH
            borderRadius: '12px',
            backgroundColor: '#2d3436',  // Fondo oscuro
            boxShadow: '0 6px 10px rgba(0,0,0,0.5)',
            overflow: 'hidden',
            display: 'flex',
            flexDirection: 'column',
            transition: 'transform 0.2s', // Un pequeño efecto al pasar el ratón (opcional)
        }}>
          
          {/* --- CABECERA (Igual que Monsters) --- */}
          <header style={{ 
              backgroundColor: '#ecf0f1', 
              padding: '12px 15px', 
              display: 'flex', 
              justifyContent: 'space-between', 
              alignItems: 'center',
              borderBottom: '2px solid #5d4037'
          }}>
            <strong style={{ fontSize: '1.1rem', color: '#2d3436', textTransform: 'uppercase' }}>
                {q.name}
            </strong>
            <span style={{
                background: '#e67e22', // Naranja para diferenciar de la amenaza roja
                color: 'white', 
                padding: '4px 8px', 
                borderRadius: '4px', 
                fontSize: '0.85rem', 
                fontWeight: 'bold'
            }}>
                ★ {q.difficulty}
            </span>
          </header>

          {/* --- CUERPO DE LA MISIÓN --- */}
          <div style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
            
            {/* Icono decorativo central (opcional, rellena el hueco de la imagen) */}
            <div style={{ 
                textAlign: 'center', 
                fontSize: '3rem', 
                marginBottom: '10px', 
                opacity: 0.8 
            }}>
                📜
            </div>

            <p style={{margin: 0, fontSize: '1rem', color: '#ecf0f1'}}>
                <span style={{color: '#2ecc71', fontWeight:'bold'}}>🎯 Objetivo:</span> 
                <br/>
                <span style={{marginLeft: '10px'}}>{q.targetMonsterName}</span>
            </p>

            <p style={{margin: 0, fontSize: '1rem', color: '#ecf0f1'}}>
                <span style={{color: '#f1c40f', fontWeight:'bold'}}>💰 Recompensa:</span> 
                <br/>
                {/* Color dorado brillante para el dinero */}
                <span style={{marginLeft: '10px', color: '#ffd700', fontWeight: 'bold'}}>{q.reward}z</span>
            </p>
          </div>

          {/* --- PIE DE TARJETA (Botón de Aceptar) --- */}
          <div style={{ padding: '15px', backgroundColor: '#1e272e', borderTop: '1px solid #5d4037', textAlign: 'center' }}>
             <button style={{
                 width: '100%',
                 backgroundColor: '#c0392b',
                 color: 'white',
                 border: 'none',
                 padding: '8px',
                 borderRadius: '4px',
                 fontWeight: 'bold',
                 cursor: 'pointer'
             }}>
                 ⚔️ Aceptar Misión
             </button>
          </div>

        </article>
      ))}
    </div>
  );
}