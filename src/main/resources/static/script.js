const API_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    loadWeapons();
    loadMonsters();
    loadQuests();
});

// --- FUNCIONES DE CARGA ---

// 1. Cargar Armas (Corrección: asegura que el endpoint es correcto)
async function loadWeapons() {
    const select = document.getElementById('hWeapon');
    try {
        const res = await fetch(`${API_URL}/hunters/weapons`);
        if(res.ok) {
            const weapons = await res.json();
            select.innerHTML = '<option value="" disabled selected>Elige Arma...</option>';
            weapons.forEach(w => {
                const opt = document.createElement('option');
                opt.value = w;
                opt.textContent = w;
                select.appendChild(opt);
            });
        } else {
            select.innerHTML = '<option>Error cargando armas</option>';
        }
    } catch (e) {
        console.error(e);
        select.innerHTML = '<option>Error de conexión</option>';
    }
}

// 2. Cargar Todos los Monstruos
async function loadMonsters() {
    fetchAndDrawMonsters(`${API_URL}/monsters`);
}

// 3. Buscar Monstruo (FILTRO)
async function searchMonster() {
    const name = document.getElementById('searchInput').value;
    if(name.trim() === "") {
        loadMonsters();
    } else {
        fetchAndDrawMonsters(`${API_URL}/monsters/search?name=${name}`);
    }
}

function resetMonsters() {
    document.getElementById('searchInput').value = "";
    loadMonsters();
}

// Función auxiliar para pintar
async function fetchAndDrawMonsters(url) {
    const list = document.getElementById('monsters-list');
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(url);
        const data = await res.json();
        list.innerHTML = '';

        if(data.length === 0) {
            list.innerHTML = '<p>No se encontraron monstruos.</p>';
            return;
        }

        data.forEach(m => {
            const div = document.createElement('div');
            div.className = 'card';
            div.innerHTML = `
                <h4>${m.name}</h4>
                <p><strong>Debilidad:</strong> <span style="color:red">${m.weakness}</span></p>
                <p><strong>Hábitats:</strong> ${m.habitats.join(', ')}</p>
            `;
            list.appendChild(div);
        });
    } catch(e) {
        list.innerHTML = '<p>Error al obtener datos.</p>';
    }
}

// 4. Cargar Misiones
async function loadQuests() {
    const ul = document.getElementById('quests-list');
    try {
        const res = await fetch(`${API_URL}/quests`);
        const data = await res.json();
        ul.innerHTML = '';
        data.forEach(q => {
            const li = document.createElement('li');
            li.innerHTML = `<strong>${q.name}</strong> <br> <small>Obj: ${q.targetMonsterName} | Recompensa: ${q.reward}z</small>`;
            ul.appendChild(li);
        });
    } catch(e) {
        ul.innerHTML = '<li>Error cargando misiones</li>';
    }
}

// --- FORMULARIO ---
document.getElementById('hunterForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById('hName').value,
        email: document.getElementById('hEmail').value,
        rank: parseInt(document.getElementById('hRank').value),
        mainWeapon: document.getElementById('hWeapon').value
    };

    const res = await fetch(`${API_URL}/hunters`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if(res.ok) {
        alert("Cazador guardado");
        document.getElementById('hunterForm').reset();
    } else {
        alert("Error al guardar (Revisa el email o datos)");
    }
});