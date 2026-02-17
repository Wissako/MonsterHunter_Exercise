import { useEffect, useState } from 'react';

export default function MonsterList() {
  const [monsters, setMonsters] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/monsters')
      .then(res => res.json())
      .then(data => setMonsters(data))
      .catch(err => console.error(err));
  }, []);

  if (monsters.length === 0) return <p style={{textAlign: 'center', marginTop: '20px', color: 'white'}}>🔍 No hay monstruos registrados.</p>;

  return (
    <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', 
        gap: '25px',
        padding: '20px'
    }}>
      {monsters.map(m => (
        <article key={m.id} style={{
            border: '2px solid #5d4037',
            borderRadius: '12px',
            backgroundColor: '#2d3436', 
            boxShadow: '0 6px 10px rgba(0,0,0,0.5)',
            overflow: 'hidden',
            display: 'flex',
            flexDirection: 'column'
        }}>
          
          <header style={{ 
              backgroundColor: '#ecf0f1', 
              padding: '10px 15px', 
              display: 'flex', 
              justifyContent: 'space-between', 
              alignItems: 'center',
              borderBottom: '2px solid #5d4037'
          }}>
            <strong style={{ fontSize: '1.2rem', color: '#c0392b', textTransform: 'uppercase' }}>{m.name}</strong>
            <span style={{
                background: '#c0392b', 
                color: 'white', 
                padding: '4px 8px', 
                borderRadius: '4px', 
                fontSize: '0.8rem', 
                fontWeight: 'bold'
            }}>
                Amenaza {m.threatLevel}
            </span>
          </header>
          
          <div style={{ 
              backgroundColor: '#1e272e', 
              padding: '15px',            
              display: 'flex', 
              justifyContent: 'center' 
          }}>
            {m.imageUrl ? (
                <img 
                    src={m.imageUrl} 
                    alt={m.name} 
                    style={{
                        width: '100%',        
                        height: '180px',      
                        objectFit: 'contain', 
                        filter: 'drop-shadow(0 0 5px rgba(0,0,0,0.5))' 
                    }} 
                />
            ) : (
                <div style={{height: '180px', color: '#bdc3c7', display: 'flex', alignItems:'center'}}>Sin Imagen</div>
            )}
          </div>

         
          <div style={{ padding: '15px' }}>
            <p style={{margin: '8px 0', fontSize: '1rem', color: '#ecf0f1'}}> 
                <span style={{color: '#2ecc71', fontWeight:'bold'}}>🦖 Tipo: </span> 
                {/* Aquí el valor ahora heredará el color blanco (#ecf0f1) */}
                {m.type} 
            </p>
            <p style={{margin: '8px 0', fontSize: '1rem', color: '#ecf0f1'}}>
                <span style={{color: '#f1c40f', fontWeight:'bold'}}>⚡ Debilidad: </span> 
                {/* Forzamos un azul claro brillante para que se lea bien */}
                <span style={{color: '#74b9ff', fontWeight: 'bold'}}>{m.weakness}</span>
            </p>
          </div>

        </article>
      ))}
    </div>
  );
}