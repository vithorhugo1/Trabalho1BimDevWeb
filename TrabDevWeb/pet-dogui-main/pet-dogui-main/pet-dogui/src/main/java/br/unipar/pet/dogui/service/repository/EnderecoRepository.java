package br.unipar.pet.dogui.service.repository;

import br.unipar.pet.dogui.model.Endereco;
import br.unipar.pet.dogui.model.Pessoa;
import br.unipar.pet.dogui.model.Servico;
import br.unipar.pet.dogui.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnderecoRepository {

    private static final String INSERT
            = "INSERT INTO endereco (nomeRua, dsbairro, nrcasa, nrcep, complemento, stAtivo, id_pessoa) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE
            = "UPDATE endereco SET nomeRua=?, dsbairro=?, nrcasa=?, nrcep=?, complemento=?, stAtivo=?, id_pessoa=?"
            + "WHERE id= ? ;";
    private static final String DELETE
            = "UPDATE endereco SET stAtivo=false WHERE id= ? ;";
    private static final String FIND_BY_ID
            = "SELECT * FROM endereco where id = ?";
    private static final String FIND_ALL
            = "SELECT * FROM endereco";

    public Endereco insert(Endereco endereco) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, endereco.getNomeRua());
            ps.setString(2, endereco.getDsBairro());
            ps.setInt(3, endereco.getNrCasa());
            ps.setString(4, endereco.getNrCep());
            ps.setString(5, endereco.getComplemento());
            ps.setBoolean(6, endereco.isStAtivo());
            ps.setInt(7, endereco.getPessoa().getId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            rs.next();
            endereco.setId(rs.getInt(1));

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return endereco;

    }

    public ArrayList<Endereco> findAll() throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Endereco> listaResultado = new ArrayList<>();

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(FIND_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {

                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("id"));
                endereco.setNomeRua(rs.getString("nomeRua"));
                endereco.setComplemento("complemento");
                endereco.setDsBairro(rs.getString("dsbairro"));
                endereco.setNrCasa(rs.getInt("nrcasa"));
                endereco.setNrCep(rs.getString("nrcep"));
                endereco.setStAtivo(rs.getBoolean("stAtivo"));
                
                int pessoa_id = rs.getInt("id_pessoa");
                Pessoa pessoa = new PessoaRepository().findById(pessoa_id);
                endereco.setPessoa(pessoa);

                listaResultado.add(endereco);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return listaResultado;

    }

    public ArrayList<Endereco> findWithParameters(String descricao) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Endereco> listaResultado = new ArrayList<>();

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(FIND_ALL
                    + " where nomeRua like '%" + descricao + "%'");
            System.out.println(ps.toString());
            rs = ps.executeQuery();

            while (rs.next()) {

                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("id"));
                endereco.setNomeRua(rs.getString("nomeRua"));
                endereco.setComplemento("complemento");
                endereco.setDsBairro(rs.getString("dsbairro"));
                endereco.setNrCasa(rs.getInt("nrcasa"));
                endereco.setNrCep(rs.getString("nrcep"));
                endereco.setStAtivo(rs.getBoolean("stAtivo"));
                
                int pessoa_id = rs.getInt("id_pessoa");
                Pessoa pessoa = new PessoaRepository().findById(pessoa_id);
                endereco.setPessoa(pessoa);

                listaResultado.add(endereco);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return listaResultado;

    }

    public void delete(int id) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, id);
            ps.execute();

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public Endereco update(Endereco endereco) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(UPDATE);
            ps.setString(1, endereco.getNomeRua());
            ps.setString(2, endereco.getDsBairro());
            ps.setInt(3, endereco.getNrCasa());
            ps.setString(4, endereco.getNrCep());
            ps.setString(5, endereco.getComplemento());
            ps.setBoolean(6, endereco.isStAtivo());
            ps.setInt(7, endereco.getPessoa().getId());
            ps.setInt(8, endereco.getId());
            ps.execute();

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return endereco;

    }

    public Endereco findById(int id) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Endereco resultado = new Endereco();

        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(FIND_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                resultado.setId(rs.getInt("id"));
                resultado.setNomeRua(rs.getString("nomeRua"));
                resultado.setComplemento("complemento");
                resultado.setDsBairro(rs.getString("dsbairro"));
                resultado.setNrCasa(rs.getInt("nrcasa"));
                resultado.setNrCep(rs.getString("nrcep"));
                resultado.setStAtivo(rs.getBoolean("stAtivo"));
                
                int pessoa_id = rs.getInt("id_pessoa");
                Pessoa pessoa = new PessoaRepository().findById(pessoa_id);
                resultado.setPessoa(pessoa);

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return resultado;

    }

}
