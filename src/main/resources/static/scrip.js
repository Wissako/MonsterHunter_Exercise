const API_URL = 'http://localhost:8080';

// 1. Cargar Monstruos al iniciar
document.addEventListener('DOMContentLoaded', () => {
    loadMonsters();
});

async function loadMonsters() {
    try {
        const response = await fetch(`${API_URL}/monsters`);
        const monsters = await response.json();

        const container = document.getElementById('cards-container');
        container.innerHTML = ''; // Limpiar

        monsters.forEach(m => {
            const card = document.createElement('div');
            card.className = 'monster-card';
            card.innerHTML = `
                <h3>${m.name}</h3>
                <p><strong>Tipo:</strong> ${m.type}</p>
                <p><span class="tag element">⚡ ${m.element || 'None'}</span> 
                   <span class="tag weakness">💀 ${m.weakness}</span></p>
                <p><strong>Amenaza:</strong> ⭐ ${m.threatLevel}</p>
                <p><strong>Hábitats:</strong> ${m.habitats.join(', ')}</p>
            `;
            container.appendChild(card);
        });
    } catch (error) {
        console.error('Error cargando monstruos:', error);
    }
}

// 2. Crear Cazador
document.getElementById('createHunterForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const hunterData = {
        name: document.getElementById('hName').value,
        email: document.getElementById('hEmail').value,
        rank: parseInt(document.getElementById('hRank').value),
        mainWeapon: document.getElementById('hWeapon').value
    };

    try {
        const response = await fetch(`${API_URL}/hunters`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(hunterData)
        });

        if (response.ok) {
            alert('¡Cazador registrado con éxito!');
            document.getElementById('createHunterForm').reset();
        } else {
            const error = await response.json();
            alert('Error: ' + (error.message || 'Datos inválidos'));
        }
    } catch (error) {
        alert('Error de conexión');
    }
});